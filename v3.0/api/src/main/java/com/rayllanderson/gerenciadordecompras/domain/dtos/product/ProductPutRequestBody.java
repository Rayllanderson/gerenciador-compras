package com.rayllanderson.gerenciadordecompras.domain.dtos.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class ProductPutRequestBody extends ProductDtoModel {
    private Long id;
    private Long categoryId;
}
