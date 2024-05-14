import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import URL from "../../tools/URL";
import HTTPRequest from "../../tools/HTTPRequest";
import {useParams} from "react-router";
import {getAdminStatus, getTechnicianStatus} from "../../tools/globals";

function CommentTable() {
    const [cookies, setCookie, removeCookie] = useCookies();

    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [comment, setComment] = useState();

    const { serviceCompletedID } = useParams();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        setLoading(true);

        HTTPRequest.get(`${URL.BACKEND}/api/user/comments/serviceCompleted/${serviceCompletedID}/comments`, cookies.JWT)
            .then(response => {
            setTableData(response.data);
            setLoading(false);
        }).catch(error => {setLoading(false); console.error(error)})
    };

    const columns = [
        {
            name: 'Comment',
            selector: row => row.comment,
            sortable: true,
            wrap: true,
            width: 'auto',
        },
        {
            name: 'Timestamp',
            selector: row =>  formatLocalDateTime(row.creationDate),
            sortable: true,
            width: '150px',
        },
    ];

    const customStyles = {
        headCells: {
            style: {
                backgroundColor: '#E7EDF0',
                color: '#333',
                paddingLeft: '12px',
                paddingRight: '12px',
            },
        },
        cells: {
            style: {
                paddingLeft: '12px',
                paddingRight: '12px',
                borderColor: '#ddd',
                cursor: 'default', // Ensures the cursor indicates the cell is not editable
            },
        },
        rows: {
            style: {
                '&:nth-of-type(even)': {
                    backgroundColor: '#E7EDF0', // Even rows background color
                },
                '&:nth-of-type(odd)': {
                    backgroundColor: '#F9FBFC', // Even rows background color
                },
                '&:hover': {
                    backgroundColor: '#ddd', // Hover row background color
                },
                borderColor: '#ddd', // Row border color
            },
        },
        pagination: {
            style: {
                marginTop: '20px', // Pagination margin top
            },
            pageButtonsStyle: {
                borderRadius: '4px', // Pagination buttons border radius
                backgroundColor: '#E7EDF0', // Pagination buttons background color
                borderColor: '#ddd', // Pagination buttons border color
                color: '#333', // Pagination buttons text color
                height: 'auto',
                padding: '8px', // Pagination buttons padding
                '&:hover': {
                    backgroundColor: '#ddd', // Pagination buttons hover background color
                },
            },
        },
    };

    const addComment = () => {
        const data = {
            comment: comment
        }
        HTTPRequest.post(`${URL.BACKEND}/api/user/comments/serviceCompleted/${serviceCompletedID}`, data, cookies.JWT)
            .then(response => {
                fetchData()
                console.log(response)
            }
        ).catch(error => {alert("Something went wrong!\nComment was not added")})

    }

    const handleCommentChange = (event) => {
        setComment(event.target.value);

    };

    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    return (
        <div style={{ margin: '20px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <div style={{ textAlign: "center" }}><h3>Comments</h3></div>
            <br />
            <div style={{ textAlign: "center", width: "60vw" }}>
                <textarea value={comment} onChange={handleCommentChange} disabled={!(getAdminStatus() || getTechnicianStatus())} style={{ width: "100%" }}></textarea>
                <br />
                <button onClick={addComment}>Add Comment</button>
            </div>
            <br />
            <div style={{ width: "60vw" }}>
                <DataTable
                    columns={columns}
                    data={tableData}
                    progressPending={loading}
                    pagination
                    persistTableHead
                    customStyles={customStyles}
                    style={{ width: "100%" }}
                />
            </div>
        </div>
    );
}

export default CommentTable;