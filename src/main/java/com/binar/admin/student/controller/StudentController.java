package com.binar.admin.student.controller;

import com.binar.admin.student.dto.StudentRequestDTO;
import com.binar.admin.student.dto.StudentResponseDTO;
import com.binar.admin.student.entity.Student;
import com.binar.admin.student.repository.StudentRedisRepo;
import com.binar.admin.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/")
@RestController
public class StudentController {

    private StudentRedisRepo studentRedisRepo;
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<?> getHome() {
        return ResponseEntity.ok("Welcome to my website");
    }

    @PostMapping("/api/v1/students")
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

    @GetMapping("/api/v1/students")
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

        return ResponseEntity.ok(studentResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentByID(@PathVariable("id") String id) {

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        Student studentRedis = studentRedisRepo.findItemById(id);

        if (studentRedis != null) {
            studentResponseDTO.setId(studentRedis.getId());
            studentResponseDTO.setName("REDIS-" + studentRedis.getName());
        } else {
            Optional<Student> student = studentRepository.findById(id);
            student.ifPresent(std -> {
                studentResponseDTO.setId(std.getId());
                studentResponseDTO.setName(std.getName());
                studentRedisRepo.save(std);
            });
        }

        return  ResponseEntity.ok(studentResponseDTO);
    }
}
