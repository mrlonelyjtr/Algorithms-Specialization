import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "jobs.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Job[] jobs = new Job[Integer.parseInt(str)];
            int index = 0;

            while ((str = reader.readLine()) != null){
                Scanner sc = new Scanner(str);
                jobs[index++] = new Job(sc.nextInt(), sc.nextInt());
            }

            reader.close();

            Arrays.sort(jobs);

            // Answer: 69119377652
            // Answer: 67311454237
            System.out.println(calTimes(jobs));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static long calTimes(Job[] jobs){
        int time = 0;
        long result = 0;

        for (Job job : jobs){
            time += job.getLength();
            result += job.getWeight() * time;
        }

        return result;
    }
}

class Job implements Comparable<Job>{
    private int weight;
    private int length;

    public Job (int weight, int length){
        this.weight = weight;
        this.length = length;
    }

    public int getWeight(){
        return weight;
    }

    public int getLength(){
        return length;
    }

    private int difference(){
        return weight - length;
    }

    private double ratio(){
        return weight / (double) length;
    }

    public int compareTo(Job job) {
        //if (this.difference() == job.difference())
        if (this.ratio() == job.ratio())
            return this.weight > job.weight ? -1 : 1;

        //return this.difference() > job.difference() ? -1 : 1;
        return this.ratio() > job.ratio() ? -1 : 1;
    }
}