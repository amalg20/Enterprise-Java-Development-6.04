package org.example.controller;


import org.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/catalogs/courses/{courseCode}")
    @ResponseStatus(HttpStatus.OK)
    public Catalog getCatalog(@PathVariable String courseCode) {

        Course course = restTemplate.getForObject("http://grades-data-service/api/courses/" + courseCode, Course.class);
        StudentGrade studentGrade = restTemplate.getForObject("http://grades-data-service/api/courses/" + courseCode + "/grades", StudentGrade.class);

        Catalog catalog = new Catalog();
        catalog.setCourseName(course.getCourseName());
        List<StudentGrade> grades = new ArrayList<>();

        //Loop over all user ratings and get the movie information for each rating
        for (Grade grade : studentGrade.getGrades()) {
            Student student = restTemplate.getForObject("http://student-info-service/api/students/" + grade.getStudentId(), Student.class);
            grades.add(new StudentGrade(student.getName(), student.getAge(), studentGrade.getGrades()));
        }

        catalog.setStudentGrades(grades);
        return catalog;
    }
}
