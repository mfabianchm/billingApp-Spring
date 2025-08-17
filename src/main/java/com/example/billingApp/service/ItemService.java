package com.example.billingApp.service;

import com.example.billingApp.io.Item.ItemRequest;
import com.example.billingApp.io.Item.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    ItemResponse add(ItemRequest request, MultipartFile file) throws IOException;

    List<ItemResponse> fetchItems();

    void deleteItem(String itemId);

}
