import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const BoardDetail = () => {

    const { boardId, problemId } = useParams();
    const [board, setBoard] = useState();

    const getBoard = async () => {
        await axios.get("http://localhost:8080/problems/" + problemId + "/boards/" + boardId)
            .then(res => {
                setBoard(res.data);
            })
            .catch(err => {
                alert(err);
            })
    }

    useEffect(() => {
        getBoard();
    }, [])

    return (
        <div>
            {board === undefined ? "" :
                <div>
                    <h1>{board.title}</h1><br />
                    <h1>{board.content}</h1><br />
                    <h1>{board.hits}</h1><br />
                </div>
            }
        </div>
    );
};

export default BoardDetail;