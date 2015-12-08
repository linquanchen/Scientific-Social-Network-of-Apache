package controllers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import models.Tag;
import models.TagRepository;

import play.mvc.Controller;
import play.mvc.Result;
import util.Common;

@Named
@Singleton
public class TagController extends Controller {

    private TagRepository tagRepository;

    @Inject
    public TagController(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Result createTag() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Tag not created, expecting Json data");
            return Common.badRequestWrapper("Tag not created, expecting Json data");
        }

        // Parse JSON file
        String tagString = json.path("tag").asText();

        try {
            if (tagRepository.findByTag(tagString) != null) {
                System.out.println("Tag already exists: " + tagString);
                return Common.badRequestWrapper("Tag already exists");
            }
            //add Tag into DB
            Tag tag = new Tag(tagString);
            tagRepository.save(tag);

            System.out.println("Tag saved: " + tag.getId());
            return created(new Gson().toJson(tag.getId()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("Tag not saved: " + tagString);
            return Common.badRequestWrapper("Tag not saved: " + tagString);
        }
    }

    public Result deleteTag() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Tag not created, expecting Json data");
            return Common.badRequestWrapper("Tag not created, expecting Json data");
        }
        // Parse JSON file
        String tagString = json.path("tag").asText();

        try {
            Tag deleteTag = tagRepository.findByTag(tagString);
            if (deleteTag == null) {
                System.out.println("Tag not found : " + tagString);
                return notFound("User not found : " + tagString);
            }

            tagRepository.delete(deleteTag);

            System.out.println("Tag is deleted: " + tagString);
            return ok("Tag is deleted: " + tagString);

        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("Tag not deleted: " + tagString);
            return Common.badRequestWrapper("Tag not deleted: " + tagString);
        }
    }

}
