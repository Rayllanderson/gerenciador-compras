import {createContext, ReactNode, useCallback, useState} from 'react';
import {SelectItem} from "../interfaces/selectItemInterface";

interface SelectedItemsContextProviderProps {
    children: ReactNode;
}

interface SelectedItemsContextContextData {
    selectedItems: SelectItem[],
    addItemToArray: (value: SelectItem) => void,
    removeItemFromArray: (value: SelectItem) => void,
    handleCheckBoxChange: (e: any) => void,
    clearSelectedItems: () => void,
    hasAnyItemSelected: () => boolean,
}

/**
 * Trata das AÇÕES dos items selecionados. Tais como:
 * Array de items selecionados, adicionar ao array, remover,
 * Conexões com a api: copiar, mover, deletar o array de items selecionados.
 */
export const SelectedItemsContext = createContext<SelectedItemsContextContextData>({} as SelectedItemsContextContextData);

export function CardItemActionProvider({children}: SelectedItemsContextProviderProps) {

    const [selectedItems, setSelectedItems] = useState<SelectItem[]>([]);

    const addItemToArray = (value: SelectItem) => {
        setSelectedItems((item => [...item, value]))
    }

    const removeItemFromArray = (value: SelectItem) => {
        setSelectedItems(item => item.filter(item => item !== value));
    }

    const hasAnyItemSelected = useCallback((): boolean => selectedItems.length > 0,
        [selectedItems])

    const handleCheckBoxChange = (e: any) => {
        const value = e.target.value;
        const selectedItem = JSON.parse(value);
        if (e.target.checked) {
            addItemToArray({id: selectedItem.id, name: selectedItem.name, isSelected: true});
        } else {
            removeItemFromArray({id: value.id, name: '', isSelected: false});
        }
    }

    const removeAllSelectedItems = () => {
        setSelectedItems([])
    }

    return (
        <SelectedItemsContext.Provider value={{
            selectedItems, addItemToArray, removeItemFromArray, handleCheckBoxChange, hasAnyItemSelected,
            clearSelectedItems: removeAllSelectedItems
        }}>
            {children}
        </SelectedItemsContext.Provider>
    )
}

