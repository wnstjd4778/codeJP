import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const InsertProblemForm = () => {


    const navigate = useNavigate();

    const [timeLimit, setTimeLimit] = useState();
    const [memoryLimit, setMemoryLimit] = useState();
    const [content, setContent] = useState();
    const [title, setTitle] = useState();
    const [category, setCategory] = useState();
    const [testCases, setTestCases] = useState();

    const insertProblem = async () => {
        await axios.post('http://localhost:8080/problem', {
            timeLimit,
            memoryLimit,
            category,
            content,
            title
        }, {
            headers: {
                'Authorization': localStorage.getItem("Authorization")
            }
        })
            .then(res => {
                console.log(res);
                console.log(localStorage.getItem("Authorization"));
                navigate('/problems');
            })
            .catch(err => {
                console.log(err);
                console.log(localStorage.getItem("Authorization"));
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
            <Form.Select aria-label="Default select example" onChange={handleCategory}>
                <option>Category</option>
                <option value="MATH">MATH</option>
                <option value="DYNAMIC_PROGRAMMING">DYNAMIC_PROGRAMMING</option>
            </Form.Select>
            <Button variant="primary" onClick={insertProblem}>
                Submit
            </Button>
        </Form>
    </div>
}

export default InsertProblemForm;
