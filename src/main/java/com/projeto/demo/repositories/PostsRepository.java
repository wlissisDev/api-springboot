package com.projeto.demo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.demo.models.PostsModel;

@Repository
public interface PostsRepository extends JpaRepository<PostsModel, UUID> {

}
