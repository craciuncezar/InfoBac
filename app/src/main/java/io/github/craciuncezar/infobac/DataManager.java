package io.github.craciuncezar.infobac;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataManager implements Serializable {

    private static volatile DataManager instance;
    private ArrayList<String> completedSubjects;
    private ArrayList<String> completedProblems;
    private HashMap<String, Integer> lessonsProgress;


    private String currentLesson;

    private DataManager(){

        //Prevent form the reflection api.
        if (instance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (DataManager.class) {
                if (instance == null) {
                    instance = new DataManager();
                }
            }
        }
        return instance;
    }

    //Make singleton from serialize and deserialize operation.
    protected DataManager readResolve() {
        return getInstance();
    }

    private static ArrayList<String> readSubjectsProgressData(Context context){
        ArrayList<String> completedSubjects = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput("completedSubjects.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            completedSubjects = (ArrayList<String>)ois.readObject();
            ois.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return completedSubjects;
    }

    private static ArrayList<String> readProblemsProgressData(Context context){
        ArrayList<String> completedProblems = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput("completedProblems.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            completedProblems = (ArrayList<String>)ois.readObject();
            ois.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return completedProblems;
    }

    private static HashMap<String, Integer> readLessonsProgressData(Context context){
        HashMap<String, Integer> completedLessons= new HashMap<>();
        try {
            FileInputStream fis = context.openFileInput("lessonsProgress.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            completedLessons = (HashMap<String, Integer>)ois.readObject();
            ois.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return completedLessons;
    }

    private static String readCurrentLesson(Context context){
        String currentLesson = "";
        try {
            FileInputStream fis = context.openFileInput("currentLesson.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            currentLesson = (String)ois.readObject();
            ois.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentLesson;
    }

    public void saveData(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("completedSubjects.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance.completedSubjects);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = context.openFileOutput("completedProblems.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance.completedProblems);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = context.openFileOutput("lessonsProgress.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance.lessonsProgress);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = context.openFileOutput("currentLesson.bin", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance.currentLesson);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readData(Context context){
        instance.completedSubjects = readSubjectsProgressData(context);
        instance.completedProblems = readProblemsProgressData(context);
        instance.lessonsProgress = readLessonsProgressData(context);
        instance.currentLesson = readCurrentLesson(context);
    }

    public ArrayList<String> getCompletedSubjects(){
        return instance.completedSubjects;
    }

    public ArrayList<String> getCompletedProblems(){
        return instance.completedProblems;
    }

    public HashMap<String, Integer> getLessonsProgress() {
        return instance.lessonsProgress;
    }

    public void setLessonsProgress(String lesson, Integer progress){
        this.lessonsProgress.put(lesson,progress);
    }

    public String getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(String currentLesson) {
        this.currentLesson = currentLesson;
    }
}
