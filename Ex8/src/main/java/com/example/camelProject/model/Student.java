package com.example.camelProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
@SqlResultSetMapping(
        name = "CourseStudentMapping",
        entities = {
                @EntityResult(entityClass = Student.class,
                        fields = {
                                @FieldResult(name = "studentid", column = "studentid"),
                                @FieldResult(name = "studentname", column = "studentname"),
                                @FieldResult(name = "course", column = "courseid")
                        }
                ),
                @EntityResult(
                        entityClass = Course.class,
                        fields = {
                                @FieldResult(name = "courseid", column = "courseid"),
                                @FieldResult(name = "coursename", column = "coursename")
                        }
                )

        }
)

public class Student {

    @Id
    @Column(name = "studentid")
    private int studentid;

    @Column(name = "studentname", nullable = false)
    private String studentname;

    @ManyToOne
    @JoinColumn(name = "courseid")
    private Course course;
}
