package com.rayllanderson.gerenciadordecompras.core.requests.products;

import com.rayllanderson.gerenciadordecompras.core.requests.SelectItemsRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Comumente usado pra mover ou copiar produtos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferProductRequestBody {
    private List<SelectItemsRequestBody> selectItems;
    private Long currentCategoryId;
    private Long newCategoryId;
}