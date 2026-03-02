package vn.edu.hcmut.cse.adsoftweng.lab.service;

import org.springframework.stereotype.Service;

import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import vn.edu.hcmut.cse.adsoftweng.lab.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(int id) {
        return this.studentRepository
                .findById(id)
                .orElse(new Student());
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentByName(String name) {
        List<Student> students = new ArrayList<>();

        for(Student student : getAllStudents()) {
            if(student.getName().contains(name)) {
                students.add(student);
            }
        }

        return students;
    }

    public void updateStudent(Student student) {
        System.out.println(student.getId());
        if (student.getId() == -1) {
            int id = 1;
            while(studentRepository.existsById(id)) {
                id = id + 1;
            }
            student.setId(id);
        }

        studentRepository.save(student);

    }

    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }



}
