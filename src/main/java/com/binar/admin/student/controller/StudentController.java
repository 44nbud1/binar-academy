package com.binar.admin.student.controller;

import com.binar.admin.student.dto.StudentRequestDTO;
import com.binar.admin.student.dto.StudentResponseDTO;
import com.binar.admin.student.entity.Student;
import com.binar.admin.student.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/students")
@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentRequestDTO studentRequestDTO) {

        String id = UUID.randomUUID().toString();
        studentRepository.save(
                Student.builder()
                        .id(id)
                        .name(studentRequestDTO.getName())
                        .createdAt(Instant.now().toEpochMilli())
                        .updatedAt(Instant.now().toEpochMilli())
                        .build()
        );

        return ResponseEntity.ok(StudentResponseDTO.builder()
                .id(id)
                .name(studentRequestDTO.getName())
                .build());

    }

    @GetMapping
    public ResponseEntity<?> getStudentList() {

        List<Student> studentList = studentRepository.findAll();



        List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();

        studentList.forEach(data -> {
            studentResponseDTOS.add(
                    StudentResponseDTO.builder()
                            .name(data.getName())
                            .id(data.getId())
                            .build()
            );
        });

        studentResponseDTOS.forEach(a -> {
            System.out.println(a);
        });

        return ResponseEntity.ok(studentResponseDTOS);
    }
}
