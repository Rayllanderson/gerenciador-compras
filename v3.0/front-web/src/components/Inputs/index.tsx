import {Form} from "react-bootstrap";
import {CheckboxContainer, InputWithIconContainer, SearchInput, InputPasswordContainer} from './styles';
import React, {useCallback, useRef, useState} from "react";
// @ts-ignore
import CurrencyInput from 'react-currency-input';
import {FiEye, FiEyeOff} from "react-icons/all";

interface Props {
    placeholder?: string;
    onChange?: (e: any) => void,
    value?: string,
    onClick?: (e: any) => void,
}

interface CheckboxProps extends Props {
    checked?: boolean;
}

interface InputWithIconsProps extends Props {
    icon: React.ComponentType;
    required: boolean;
    type: string;
}

export function InputText({placeholder, value, onChange}: Props) {
    return (
        <input type={'text'} placeholder={placeholder}
               className="form-control form-control-lg" onChange={onChange} value={value}/>
    )
}

export function InputPassword({placeholder, value, onChange}: Props) {
    const [isVisible, setIsVisible] = useState(false);
    const toggleVisibility = useCallback(() => {
        if (isVisible) setIsVisible(false)
        else setIsVisible(true);
    }, [isVisible])
    return (
        <InputPasswordContainer className="input-group mb-3">
            <input type={isVisible ? 'text' : 'password'} className="form-control"
                   placeholder={placeholder} aria-describedby="basic-addon2"
            value={value} onChange={onChange} />
            <span className="input-group-text" onClick={toggleVisibility} > {isVisible ? <FiEyeOff/> : <FiEye/>}</span>
        </InputPasswordContainer>
    )
}

export function InputSearch({placeholder, value, onChange}: Props) {
    return (
        <SearchInput type="search" className="rounded-md py-1 px-3 form-control"
                     value={value} onChange={onChange}
                     placeholder={placeholder}/>
    )
}

export function InputNumber({value, onChange}: Props) {
    return (
        <CurrencyInput value={value} decimalSeparator="," thousandSeparator="."
                       onChangeEvent={onChange} className={'form-control form-control-lg'}/>
    )
}


export function InputCheckbox({onChange, value, checked, onClick}: CheckboxProps) {
    return (
        <CheckboxContainer>
            <Form.Check type="checkbox" className="checkbox" onChange={onChange} value={value}
                        checked={checked} onClick={onClick}/>
        </CheckboxContainer>
    )
}

export function InputWithIcon({onChange, value, placeholder, icon: Icon, required, type}: InputWithIconsProps) {
    const inputRef = useRef<HTMLInputElement>(null);
    const [isFocused, setIsFocused] = useState(false)
    const [isFilled, setIsFilled] = useState(false)

    const handleInputBlur = useCallback(() => {
        setIsFocused(false);
        const hasValue = !!inputRef.current?.value;
        setIsFilled(hasValue)
    }, []);

    return (
        <InputWithIconContainer className="input-group" isFocused={isFocused} isFilled={isFilled}>
            <span className="input-group-text">
                 <Icon/>
            </span>
            <input className="form-control form-control-lg inner-addon left-addon"
                   onChange={onChange}
                   value={value}
                   type={type}
                   placeholder={placeholder}
                   onFocus={() => setIsFocused(true)}
                   onBlur={handleInputBlur}
                   ref={inputRef} required={required}/>
        </InputWithIconContainer>
    )
}
