package ru.job4j.grabber;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {

    private List<Post> listJob = new ArrayList<>();

    private int ids = 1;

    private int indexOf(int id) {
        for (int index = 0; index < listJob.size(); index++) {
            if (listJob.get(index) != null && listJob.get(index).getId() == id) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public void save(Post post) {
        post.setId(ids++);
        listJob.add(post);
    }

    @Override
    public List<Post> getAll() {
        return new ArrayList<Post>(listJob);
    }

    @Override
    public Post findById(int id) {
        int index = indexOf(id);
        return index != -1 ? listJob.get(index) : null;
    }

}
