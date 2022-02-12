import React from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';

function Header() {
    return <div>
        <Navbar bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/">Navbar</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link href="/">Home</Nav.Link>
                    {localStorage.getItem("Authorization") === undefined ? <Nav.Link href='/users/login'>Login</Nav.Link> : <Nav.Link href='/users/myPage'>MyPage</Nav.Link>}
                    <Nav.Link href="/problems">Problem</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
        <br/>
    </div>;
}

export default Header;
