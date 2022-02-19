import axios from 'axios';
import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';

const MyInfo = () => {

    const [user, setUser] = useState();

    const getUser = async () => {
        await axios.get("http://localhost:8080/user", {
            headers: {
                "Authorization" : localStorage.getItem("Authorization")
            }
        })
            .then(res => {
                setUser(res.data);
            })
            .catch(err => {
                alert(err);
            })
    }

    useState(() => {
        getUser()
    }, []);

    return (
        <div>
            {user === undefined ? "" :
                <div>
                    <Form style={{textAlign: 'center', margin: 'auto', width: '50%'}}>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label >email</Form.Label>
                    <h2>{user.email}</h2><br />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>tel</Form.Label>
                    <h2>{user.tel}</h2><br />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>name</Form.Label>
                    <h2>{user.name}</h2><br />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>answerCount</Form.Label>
                    <h2>{user.answerCount}</h2><br />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>submitCount</Form.Label>
                    <h2>{user.submitCount}</h2><br />
                </Form.Group>
                <Button variant="primary">
                    Submit
                </Button>
            </Form>        
                </div>
            }
        </div>
    );
};

export default MyInfo;

