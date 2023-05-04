package de.haizon.pixelcloud.master.console;

import de.haizon.pixelcloud.api.setup.ISetup;
import de.haizon.pixelcloud.api.setup.Question;
import de.haizon.pixelcloud.master.CloudMaster;


import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SetupWrapper {

    private ISetup currentSetup;
    private Question currentQuestion;

    private final BlockingQueue<Question> queuedQuestions = new LinkedBlockingQueue<>();

    public void run(ISetup setup) {

        if (currentSetup != null) {
            CloudMaster.getInstance().getCloudLogger().severe("A setup is already running!");
            return;
        }

        currentSetup = setup;

        Class<?> clazz = setup.getClass();

        List<Question> questions = new LinkedList<>();

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Question.class)) {
                questions.add(method.getAnnotation(Question.class));
            }
        }

        queuedQuestions.addAll(questions);

        if (queuedQuestions.isEmpty()) {
            CloudMaster.getInstance().getCloudLogger().severe("No questions found!");
            return;
        }

        Question question = queuedQuestions.poll();
        currentQuestion = question;

        CloudMaster.getInstance().getCloudLogger().info(question.question());

    }

    public void handle(String input){
        Class<?> clazz = currentSetup.getClass();

        for(Method method : clazz.getMethods()){
            if(method.isAnnotationPresent(Question.class)){
                Question question = method.getAnnotation(Question.class);
                System.out.println(question.id());
                assert queuedQuestions.peek() != null;
                if(question.id() == currentQuestion.id()){
                    try {
                        System.out.println("test");
                        if((boolean) method.invoke(currentSetup, input)){
                            next();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void cancel(){
        if(currentSetup == null){
            CloudMaster.getInstance().getCloudLogger().severe("No setup is running!");
            return;
        }
        currentSetup = null;
        queuedQuestions.clear();
        CloudMaster.getInstance().getCloudLogger().info("Setup cancelled!");
    }

    public void next(){
        Question question = queuedQuestions.poll();
        if(question == null){
            currentSetup.finished();
            currentQuestion = null;
            currentSetup = null;
            return;
        }
        currentQuestion = question;
        CloudMaster.getInstance().getCloudLogger().info(question.question());
    }

    public boolean isRunning() {
        return currentSetup != null;
    }

}
