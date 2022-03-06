import axios from 'axios';
import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate, useSearchParams } from 'react-router-dom';

const InsertTestCase = () => {

    const [input, setInput] = useState();
    const [output, setOutput] = useState();
    const navigate = useNavigate();

    const handleInput = (e) => {
        setInput(e.target.value);
    }

    const handleOutput = (e) => {
        setOutput(e.target.value);
    }

    const insertTestcase = async() => {
        await axios.post("http://localhost:8080/testcase/"+ problemId, {
            parameter: input,
            expectedData: output
        })
            .then((res) => {
                
            })
            .catch((err) => {
                alert(err);
            })
    }
    return (
        <div>
            <Form>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>input</Form.Label>
                <Form.Control onChange={handleInput} />
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>output</Form.Label>
                <Form.Control onChange={handleOutput} />
            </Form.Group>
            <Button variant="primary" onClick={insertTestcase}>
                Submit
            </Button>
        </Form>
        </div>
    );
};

export default InsertTestCase;