package com.danghieu99.monolith.product.mapper;

import com.danghieu99.monolith.product.dto.request.SellerSaveProductRequest;
import com.danghieu99.monolith.product.dto.request.SellerSaveVariantRequest;
import com.danghieu99.monolith.product.dto.response.GetProductDetailsResponse;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ProductMapper {

    @Mappings({@Mapping(target = "shopId", ignore = true),
    })
    Product toProduct(SellerSaveProductRequest request);

    GetProductDetailsResponse toGetProductDetailsResponse(Product product);

    Variant toVariant(SellerSaveVariantRequest request);

}
