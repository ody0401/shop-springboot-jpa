package com.shop.dto;

import com.shop.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Slf4j
@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {

    private String category;
    private String list;

    private Sort sort;

    private int page;
    private int size;

    public PageRequestDto() {
        this.page = 1;
        this.size = 8;
        this.list = "";
    }

    public Category getCategoryType() {

        switch (category) {
            case "top":
                return Category.TOP;
            case "bottom":
                return Category.BOTTOM;
            case "shirts":
                return Category.SHIRTS;
            case "shoes":
                return Category.SHOES;
            case "outer":
                return Category.OUTER;
            default:
                return null;
        }
    }

    private Sort listToSort(){
        switch (list) {
            case "lowPrice":
                return Sort.by("price").ascending();
            case "highPrice":
                return Sort.by("price").descending();
            case "name":
                return Sort.by("productName").ascending();
            case "new":
            default:
                return Sort.by("id").descending();
        }

    }

    public Pageable getPageable(){

        this.sort = listToSort();
        return PageRequest.of(page - 1, size, sort);
    }
}
