package com.example.commerce.business.item.service;


import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.user.domain.User;

import java.util.List;

public interface ItemImageService {

    List<ItemImage> createItemImage(User user, List<String> imagePaths);
    ItemImage createItemImage(User user, String imagePath);
}
