import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Form, Button} from 'react-bootstrap';

const InsertProblemForm = () => {


    const [timeLimit, setTimeLimit] = useState();
    const [memoryLimit, setMemoryLimit] = useState();
    const [content, setContent] = useState();
    const [title, setTitle] = useState();
    const [category, setCategory] = useState();

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

    const handleTitle = (e) => {
        setTitle(e.target.value);
    };

    const handleContent = (e) => {
        setContent(e.target.value);
    };

    const handleTimeLimit = (e) => {
        setTimeLimit(e.target.value);
    };

    const handleMemoryLimit = (e) => {
        setMemoryLimit(e.target.value);
    };

    const handleCategory = (e) => {
        setCategory(e.target.value);
    };



    return <div>
        <Form>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>title</Form.Label>
                <Form.Control onChange={handleTitle} />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>content</Form.Label>
                <Form.Control onChange={handleContent} />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>timeLimit</Form.Label>
                <Form.Control onChange={handleTimeLimit} />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>memoryLimit</Form.Label>
                <Form.Control onChange={handleMemoryLimit} />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>category</Form.Label>
                <Form.Control onChange={handleCategory} />
            </Form.Group>
            <Button variant="primary" onClick={insertProblem}>
                Submit
            </Button>
        </Form>
    </div>;
}

export default InsertProblemForm;
