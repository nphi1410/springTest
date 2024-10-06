/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TodoList2.Model;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author AD
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")

public class TodoDTO {

    @Id
    private String id;

    @NotNull(message = "todo cannot be null")
    private String todo;

    @NotNull(message = "description cannot be null")
    private String description;

    
    @NotNull(message = "completed cannot be null")
    private boolean completed;

    private Date createAt;

    private Date updateAt;

}
