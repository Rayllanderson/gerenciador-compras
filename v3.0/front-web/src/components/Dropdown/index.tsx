import {Dropdown} from "react-bootstrap";
import {FiHelpCircle, FiLogOut, FiMoreVertical, FiPieChart, FiSettings} from "react-icons/all";
import {Link} from 'react-router-dom';
import React, {useCallback, useContext, useEffect, useState} from "react";
import {DropdownContent, DropdownNavbarImage, NavBarContent} from './styles';
import {FiUser} from "react-icons/fi";
import {VisibilityCardItemContext} from "../../contexts/CardItemVisibilityContext";
import {LogoutContext} from "../../contexts/LogoutContext";
import UserController from "../../controllers/userController";
import {AccountContext} from "../../contexts/AccountContext";

interface ActionDropdownProps {
    filterAction: () => void
}

export function DropdownNavbar() {
    const {logout} = useContext(LogoutContext);
    const {hasChangedImage} = useContext(AccountContext);

    const [miniature, setMiniature] = useState<string>('');

    useEffect(() => {
        new UserController().findMiniature().then((response) => setMiniature(response.data));
    }, [hasChangedImage])

    const closeDropdown = useCallback(() => {
        // @ts-ignore
        document.getElementById('dropdown').click();
    }, [])
    return (
        <NavBarContent hasImage={!!miniature}>
            <Dropdown className="content-items" drop='down'>
                <Dropdown.Toggle variant="dropdown" id={'dropdown'}>
                    {
                        miniature ?
                            <DropdownNavbarImage src={miniature} alt={'miniature'} width={30}/>
                            :
                            < FiSettings size={21}/>
                    }
                </Dropdown.Toggle>
                <Dropdown.Menu className="drop-menu">
                    <Link to={'/account'} onClick={closeDropdown} className={'dropdown-item'}> <FiUser/> Minha Conta </Link>
                    <Link to={'/statistics'} onClick={closeDropdown} className={'dropdown-item'}> <FiPieChart/> Estatísticas </Link>
                    <Link to={'/help'} onClick={closeDropdown} className={'dropdown-item'}> <FiHelpCircle/> Ajuda</Link>
                    <Dropdown.Header> </Dropdown.Header>
                    <Dropdown.Item onClick={logout}><FiLogOut/> Logout</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        </NavBarContent>
    )
}

export function ActionDropdown({filterAction}: ActionDropdownProps) {

    const {showCheckBox, hideCheckBox, checkBoxIsVisible, showDeleteButton, hideDeleteButton
        , hideEditButton, showEditButton, editButtonIsVisible, deleteButtonIsVisible} = useContext(VisibilityCardItemContext);


    const handleCheckBoxClick = () => {
        checkBoxIsVisible ? hideCheckBox() : showCheckBox();
    }

    const handleEditClick = () => {
        editButtonIsVisible ? hideEditButton() : showEditButton();
    }

    const handleDeleteClick = () => {
        deleteButtonIsVisible ? hideDeleteButton() : showDeleteButton();
    }

    return (
        <DropdownContent >
            <Dropdown drop='down'>
                <Dropdown.Toggle variant="dropdown" className="button-dropdown button button-primary"
                                 style={{borderBottomLeftRadius: 0, borderTopLeftRadius: 0}}>
                    <FiMoreVertical size={21} color={'#ffff'}/>
                </Dropdown.Toggle>
                <Dropdown.Menu className="drop-menu">
                    <Dropdown.Header className="drop-header">Ações</Dropdown.Header>
                    <Dropdown.Item onClick={handleCheckBoxClick}>Selecionar</Dropdown.Item>
                    <Dropdown.Item onClick={handleEditClick}>Editar</Dropdown.Item>
                    <Dropdown.Item onClick={handleDeleteClick}>Remover</Dropdown.Item>

                    <Dropdown.Header className="drop-header">Filtros</Dropdown.Header>
                    <Dropdown.Item onClick={filterAction}>Filtrar</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        </DropdownContent>
    )
}