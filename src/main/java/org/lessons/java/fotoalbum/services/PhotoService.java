package org.lessons.java.fotoalbum.services;

import java.util.List;

import org.lessons.java.fotoalbum.models.Category;
import org.lessons.java.fotoalbum.models.Photo;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
	public void setPhotoCategoriesToString(Photo photo) {
		String categoriesToString = "";
		List<Category> categoryList = photo.getCategories();
		for (Category category : categoryList) {
			categoriesToString += category.toString() + ", ";
		}
		categoriesToString = categoriesToString.substring(0, categoriesToString.length() - 2);
		photo.setCategoriesToString(categoriesToString);
	}
}
