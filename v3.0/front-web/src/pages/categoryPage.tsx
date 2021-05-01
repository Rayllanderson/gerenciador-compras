import React, {useContext} from "react";
import Header from "../components/Header";
import {FiList} from "react-icons/all";
import Search from "../components/Search";
import {ButtonGroup} from "../components/ButtonsGroup";
import CategoryList from "../components/Card/category/CategoryList";
import {CategoryModal} from "../components/Modal/category/CategoryModal";
import {DeleteModal} from "../components/Modal/DeleteModal";
import {MyPagination} from "../components/Paginations/Pagination";
import CategoryController from "../controllers/categoryController";
import {SelectItemsButtons} from "../components/ButtonsGroup/selectItemsButtons";
import {CyanSecondaryButton, RedButton} from '../components/Buttons/styles'
import {CategoryContext} from "../contexts/CategoryContext";
import {ActionModalContext} from "../contexts/ActionModalContext";

export default function CategoryPage() {
    const {setToSave, remove, selectedCategory} = useContext(CategoryContext);
    const {duplicateCategoryAction, removeVariousCategoriesAction} = useContext(ActionModalContext);
    const controller = new CategoryController();

    return (
        <div>
            <Header title={'Listas'} Icon={FiList}/>
            <Search placeholder={'Procurar uma lista...'} action={() => {
            }}/>
            <ButtonGroup add={setToSave}/>

            <CategoryList/>

            <MyPagination controller={controller}/>

            <SelectItemsButtons>
                <CyanSecondaryButton className={'btn '} title={'Duplicar selecionados'}
                                     onClick={duplicateCategoryAction}>Duplicar</CyanSecondaryButton>
                <RedButton className={'btn ms-4'} title={'Deletar selecionados'}
                           onClick={removeVariousCategoriesAction}
                >Deletar </RedButton>
            </SelectItemsButtons>

            <CategoryModal/>
            <DeleteModal text={`Você tem certeza que deseja excluir a lista ${selectedCategory.name}`}
                         action={remove}/>
        </div>
    );
}