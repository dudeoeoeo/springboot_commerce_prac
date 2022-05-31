package com.example.commerce.business.item.service;

import com.example.commerce.business.item.domain.ItemImage;
import com.example.commerce.business.item.repository.ItemImageRepository;
import com.example.commerce.business.user.domain.User;
import com.example.commerce.common.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemImageServiceImpl implements ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final AmazonS3Service amazonS3Service;

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

    @Transactional
    public void updateItemImage(Long imageId, String imagePath) {
        final ItemImage image = findById(imageId);
        image.updateImage(imagePath);
        itemImageRepository.save(image);
    }

    @Transactional
    public void deleteItemImage(Long imageId, User user) {
        final ItemImage image = findById(imageId);
        image.deleteImage(user);
        final ItemImage save = itemImageRepository.save(image);
    }

    public ItemImage findById(Long imageId) {
        return itemImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지를 찾을 수 없습니다."));
    }
}
