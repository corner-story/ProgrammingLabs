package com.java.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GUI extends JFrame {

    private List<Student> students = new ArrayList<>();
    public int size = 4;
    public String[] names = {"姓名", "年龄", "出生日期", "成绩"};

    JPanel[] panels = new JPanel[size];
    JLabel[] labels = new JLabel[size];
    JTextField[] textField = new JTextField[size];
    JButton button = new JButton("添加信息");
    JTextArea textArea = new JTextArea(50, 100);

    public GUI(){

        this.setTitle("学生成绩查看");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        for (int i = 0; i < names.length; i++) {
            panels[i] = new JPanel();
            labels[i] = new JLabel(names[i]);
            textField[i] = new JTextField(20);
            panels[i].add(labels[i]);
            panels[i].add(textField[i]);
            this.add(panels[i]);
        }
        this.add(button, BorderLayout.CENTER);
        this.add(textArea, BorderLayout.CENTER);


        //添加监听函数
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String name = textField[0].getText();
                    int age = Integer.valueOf(textField[1].getText());
                    String birth = textField[2].getText();
                    double score = Double.valueOf(textField[3].getText());
                    students.add(new Student(name, age, birth, score));
                    clearStudent();
                    printStudents();

                }catch (Exception msg){
                    //clearStudent();
                    System.out.println(msg);
                }
            }
        });


        this.setSize(1100, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setVisible(true);
    }

    public void clearStudent(){
        for (int i = 0; i < textField.length; i++) {
            textField[i].setText("");
        }
    }


    public void printStudents(){
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if(o1.age > o2.age){
                    return 1;
                }else  if(o1.age < o2.age){
                    return -1;
                }
                return 0;
            }
        });
        textArea.setText("");
        students.forEach(student -> {
            textArea.append(student.toString());
            textArea.append("\n");
        });
    }



    public static void main(String[] args) {
        GUI gui = new GUI();

    }

}
