import React, { useEffect } from 'react';
import axios from 'axios';
import { Form, Button} from 'react-bootstrap';

const InsertProblemForm = () => {

    const [timeLimit, setTimeLimit] = useEffect();
    const [memoryLimit, setMemoryLimit] = useEffect();
    const [content, setContent] = useEffect();
    const [title, setTitle] = useEffect();
    const [category, setCategory] = useEffect();

    const insertProblem = async () => {
        await axios.post('http://localhost:8080/problem', {
            timeLimit,
            memoryLimit,
            category,
            content,
            title
        })
            .then(res => {
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })
    };

    return <div>
        <Form>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control type="email" placeholder="Enter email" />
                <Form.Text className="text-muted">
                    We'll never share your email with anyone else.
                </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicCheckbox">
                <Form.Check type="checkbox" label="Check me out" />
            </Form.Group>
            <Button variant="primary" type="submit">
                Submit
            </Button>
        </Form>
    </div>;
}

export default InsertProblemForm;
