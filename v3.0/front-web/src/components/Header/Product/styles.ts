import styled from "styled-components";

export const Container = styled.div`
    max-width: 550px;
    text-align: center;
    margin: 0 auto;
    
    background: ${props => props.theme.colors.backgroundSecondary};
    color: ${props => props.theme.colors.primary};
    border: 2px solid ${props => props.theme.colors.primary}!important;
    
    
    border-radius: 5px;
    padding: 10px;
    
    p{
        line-height: 1.1;
        color: ${props => props.theme.colors.text};
        opacity: 1;
    }
    
    .icon{
        cursor: pointer;
        transition: 0.2s;
    }
    
    .icon-down{
         transform: rotate(0);
     &:hover{
            transform: rotate(0) translateY(5px)!important;
        }
     }
     
     .icon-up{
        transform: rotate(-180deg);
         &:hover{
                 transform: rotate(-180deg) translateY(5px)!important;
            }
     }
     
     .card{
         box-shadow: 0px 7px 10px 1px rgba(0,0,0,0.02)
     }
     
    .card-content{
        background: ${props => props.theme.colors.backgroundSecondary};
        border: 1px solid ${props => props.theme.title === 'light' ? 'white' : 'black'};
    }
    
    ${props => props.theme.title === 'dark' && `
    .collapse, .collapsing{
    background: #29292e!important;
    }`};
    
    
    .card-content-header{
        cursor: pointer;
    }
    
    .center-left{
        display: inline-block;
        text-align: left
    }
     
`