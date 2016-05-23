package com.d500px.fivehundredpx.util;

import com.d500px.fivehundredpx.model.Photo;
import com.d500px.fivehundredpx.model.PhotoImage;
import com.d500px.fivehundredpx.model.PhotoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KD on 5/4/16.
 */
public class MockModelPhotoResponse {

    public static final int DEFAULT_PHOTOS_COUNT = 20;

    private static final String JPEG = "jpeg";
    private static final String FAKE_URL = "https://drscdn.500px.org/photo/152179093/w%3D600_h%3D600/1592944850e1f902d3751e33a7ce16c3?v=3";

    public static PhotoResponse generateMockPhotoResponse(int photosCount) {
        return new PhotoResponse(generateMockPhotoCollection(photosCount), 1);
    }

    private static List<Photo> generateMockPhotoCollection(int count) {
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Photo photo = new Photo(generateMockPhotoImageCollection(2));
            photos.add(photo);
        }
        return photos;
    }

    private static List<PhotoImage> generateMockPhotoImageCollection(int imageSizes) {
        List<PhotoImage> photoImages = new ArrayList<>();
        for (int i = 0; i < imageSizes; i++) {
            photoImages.add(makeMockPhotoImage());
        }
        return photoImages;
    }

    private static PhotoImage makeMockPhotoImage() {
        return new PhotoImage(600, FAKE_URL, JPEG);
    }
}
