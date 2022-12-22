import Link from 'next/link';
import Image from "next/image";
import {Button, Form} from "react-bootstrap";
import {useEffect, useState} from "react";

export default () => {
    const [isAuth, setIsAuth] = useState('');
    useEffect(() => {
        setIsAuth(localStorage.getItem("auth-token"));
    }, []);

    const links = [
        !isAuth && {label: 'Sign Up', href: '/auth/signup'},
        !isAuth && {label: 'Login', href: '/auth/login'},
        isAuth && {label: 'My Orders', href: '/orders'},
        isAuth && {label: 'Sign Out', href: '/auth/logout'},
    ]
        .filter((linkConfig) => linkConfig)
        .map(({label, href}) => {
            return (
                <li key={href} className="nav-item">
                    <Link href={href} className="nav-link text-black mx-md-3">
                        {label}
                    </Link>
                </li>
            );
        });

    return (
        <nav className="navbar bg navbar-light bg-warning shadow-lg">
            <Button className="navbar-toggler-icon bg-warning btn-outline-warning mx-md-2"/>
            <Link href="/" className="navbar-brand position-absolute mx-md-5">
                Zunami
                <Image src="/surfboard.svg" alt="surfboard"
                       width={40} height={40}/>
            </Link>
            <Form className="d-flex col-md-4 justify-content-center position-relative">
                <Form.Control type="search" placeholder="Search" className="me-2" aria-label="Search"/>
            </Form>

            <Link legacyBehavior={true} href="/products">
                <a className="nav-link">BROWSE</a>
            </Link>

            <div className="d-flex justify-content-end mx-md-5">
                <ul className="nav d-flex align-items-center">
                    <li key="/cart" className="nav-item">
                        <Link href="/cart" className="nav-link text-black">
                            <Image src="/cart.svg" alt="cart"
                                   width={30} height={30}/>
                        </Link>
                    </li>
                    {links}</ul>
            </div>
        </nav>
    );
};
