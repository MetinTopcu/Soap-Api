package com.apinizer.example.soap;

import com.apinizer.example.xml.Student;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentRepository {
    private static final Map<String, Student> students = new HashMap<>();

    @PostConstruct
    public void initData() {

        Student student = new Student();
        student.setName("Apinizer");
        student.setStandard(5);
        student.setAddress("Demo");
        student.setDate(new Date());
        students.put(student.getName(), student);

        student = new Student();
        student.setName("Pruvasoft");
        student.setStandard(5);
        student.setAddress("Bilkent");
        student.setDate(new Date());
        students.put(student.getName(), student);

    }

    public Student findStudent(String name) {
        Assert.notNull(name, "The Student's name must not be null");
        return students.get(name);
    }

    public Student findStudent(Student student) {
        return student;
    }
}