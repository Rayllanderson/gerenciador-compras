import {createContext, ReactNode, useCallback, useState} from 'react';
import api from '../services/api';

interface SignInCredentials {
    username: string;
    password: string;
}

interface SignUpCredentials {
    name: string;
    username: string;
    password: string;
}

interface AuthContextData {
    user: object;
    signIn: (credentials: SignInCredentials) => Promise<void>
    signUp: (credentials: SignUpCredentials) => Promise<void>
    signOut: () => void;
}

interface AuthProviderProps {
    children: ReactNode;
}

interface AuthState {
    token: string;
    user: object;
}

export const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export function AuthProvider({children}: AuthProviderProps) {
    const [data, setData] = useState<AuthState>(() => {
        const token = localStorage.getItem('@GerenciadorDeCompras:token');
        const user = localStorage.getItem('@GerenciadorDeCompras:user');
        if (token && user && user !== 'undefined') {
            return {token, user: JSON.parse(user)};
        }
        return {} as AuthState;
    });

    const signIn = useCallback(async ({username, password}: SignInCredentials) => {
        const response = await api.post('login', {
            username, password
        });
        const {token, name} = response.data;
        const user = {username, name};
        localStorage.setItem('@GerenciadorDeCompras:token', token);
        localStorage.setItem('@GerenciadorDeCompras:user', JSON.stringify(user));
        setData({token, user});
    }, [])

    const signUp = useCallback(async ({name, username, password}) => {
        await api.post('users', {
            name, username, password
        });
    }, [])

    const signOut = useCallback(() => {
        localStorage.removeItem('@GerenciadorDeCompras:token');
        localStorage.removeItem('@GerenciadorDeCompras:user');
        setData({} as AuthState)
    }, [])

    return (
        <AuthContext.Provider value={{user: data.user, signIn, signOut, signUp}}>
            {children}
        </AuthContext.Provider>
    )
}

