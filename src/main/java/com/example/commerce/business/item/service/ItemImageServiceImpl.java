package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.repository.ItemImageRepository;
import com.example.commerce.business.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemImageServiceImpl implements ItemImageService {

    private final ItemImageRepository itemImageRepository;

    @Transactional
    public List<ItemImage> createItemImage(User user, List<String> imagePaths) {
        AtomicInteger idx = new AtomicInteger();
        final List<ItemImage> itemImages = imagePaths.stream()
                .map(imageUrl -> ItemImage.newItemImage(user, imageUrl, idx.getAndIncrement() + 1))
                .collect(Collectors.toList());
        return itemImageRepository.saveAll(itemImages);
    }

    @Transactional
    public ItemImage createItemImage(User user, String imagePath) {
        final ItemImage itemImage = ItemImage.newItemImage(user, imagePath, 1);
        return itemImageRepository.save(itemImage);
    }
}
