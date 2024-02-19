package com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.repository.PostJpaRepository;
import com.blog.repository.PostRepository;
import com.blog.vo.Post;

import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

	private static List<Post> posts;

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostJpaRepository jpaRepository;

	public Post getPost(Long id) {
		Post post = jpaRepository.findOneById(id);
		return post;
	}

	public List<Post> getPosts() {
		List<Post> post = jpaRepository.findAllByOrderByUpdtDateDesc();
//		posts = new ArrayList<>();
//		posts.add(new Post(1L, "Mike", "Mike's Post", "Welcome to My blog"));
//		posts.add(new Post(2L, "Jason", "It's Jason", "Hi, My name is Jason"));

		return post;
	}

	public List<Post> getPostsOrderByUpdtAsc() {
		List<Post> postList = postRepository.findPostOrderByUpdtDateAsc();
		return postList;
	}

	public List<Post> getPostsOrderByRegDesc() {
		List<Post> postList = postRepository.findPostOrderByRegDateDesc();
		return postList;
	}

	public List<Post> searchPostByTitle(String query) {
		List<Post> posts = postRepository.findPostLikeTitle(query);
		return posts;
	}

	public List<Post> searchPostByContent(String query) {
		List<Post> posts = postRepository.findPostLikeContent(query);
		return posts;
	}

	public boolean savePost(Post post) {
		Post result = jpaRepository.save(post);
		boolean isSuccess = true;

		if (result == null) {
			isSuccess = false;
		}

		return isSuccess;
	}

	public boolean deletePost(Long id) {

		Post result = jpaRepository.findOneById(id);

		if (result == null)

			return false;

		jpaRepository.deleteById(id);

		return true;

	}
	
	public boolean updatePost(Post post) {
	    // Mencari post berdasarkan ID
	    Post result = jpaRepository.findOneById(post.getId());

	    // Jika post tidak ditemukan, kembalikan false
	    if (result == null) {
	        return false;
	    }

	    // Memperbarui judul jika tidak kosong
	    if (!StringUtils.isEmpty(post.getTitle())) {
	        result.setTitle(post.getTitle());
	    }

	    // Memperbarui konten jika tidak kosong
	    if (!StringUtils.isEmpty(post.getContent())) {
	        result.setContent(post.getContent());
	    }

	    // Menyimpan perubahan ke dalam database
	    jpaRepository.save(result);

	    // Mengembalikan true karena pembaruan berhasil
	    return true;
	}
	
	public List<Post> searchPostByTitle2(String query) {
	    List<Post> posts = jpaRepository.findByTitleContainingOrderByUpdtDateDesc(query);
	    return posts;
	}
	
	public List<Post> searchPostByContent2(String query) {
	    List<Post> posts = jpaRepository.findByContentContainingOrderByUpdtDateDesc(query);
	    return posts;
	}



}