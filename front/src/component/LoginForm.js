import axios from 'axios';
import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function LoginForm() {

    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const navigate = useNavigate();

    const handleEmail = (e) => {
        setEmail(e.target.value);
    }

    const handlePassword = (e) => {
        setPassword(e.target.value);
    }

    const signIn = async () => {

        await axios.post('http://localhost:8080/user/signIn', {
            email,
            password
        })
            .then(res => {
                localStorage.setItem('Authorization', res.data.tokenType + " " + res.data.accessToken);
                navigate('/');
            })
            .catch(err => {
                console.log(err);
            })
    }
    return <div>
        <Form>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control onChange={handleEmail} />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>password</Form.Label>
                <Form.Control onChange={handlePassword} />
            </Form.Group>
            <Button variant="primary" onClick={signIn}>
                Submit
            </Button>
        </Form>
    </div>;
}

export default LoginForm;
