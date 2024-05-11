import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import URL from "../../tools/URL";
import HTTPRequest from "../../tools/HTTPRequest";
import {jwtDecode} from "jwt-decode";
import {getAdminStatus} from "../../tools/globals";

function Table() {
    const [cookies, setCookie, removeCookie] = useCookies();

    const [data, setData] = useState([]);
    const [searchData, setSearchData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const [isAdmin, setIsAdmin] = useState(false);

    // If user doesn't have a JWT cookie it will redirect them to the login page.
    useEffect(() => {
        if(cookies.JWT == null) {
            navigate('/login');
        }
    }, []);


    useEffect(() => {
        fetchData();
    }, []);

    const search = (event) => {
        setSearchData([]);
        data.forEach(element => {
            if(element.name.toLowerCase().includes(event.target.value.toLowerCase()) || element.id.toString().includes(event.target.value.toLowerCase()) || element.partNumber.toLowerCase().includes(event.target.value.toLowerCase())) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const create = () => {
        navigate('/asset/create');
    };

    const fetchData = async () => {
        setLoading(true);
        HTTPRequest.get(`${URL.BACKEND}/api/assets`, cookies.JWT).then(response => {
            setData(response.data);
            setTableData(response.data);
            setLoading(false);
        }).catch(error => {setLoading(false)});
    };

    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    const columns = [
        {
            name: 'Part Number',
            selector: row => row.partNumber,
            sortable: true,
            width: '150px',
        },
        {
            name: 'Name',
            selector: row => row.name,
            sortable: true,
            width: '250px',
        },
        {
            name: 'Description',
            selector: row => row.description,
            sortable: true,
            width: '500px',
        },
        {
            name: 'Creation Date',
            selector: row => formatLocalDateTime(row.creationDate),
            sortable: true,
            width: 'auto',
        },
        {
            name: 'Active',
            selector: row => row.active ? 'Yes' : 'No',
            sortable: true,
            width: 'auto',
        },
    ];

    // Handler for row click event using navigate
    const handleRowClicked = (row) => {
        navigate(`/asset/${row.id}`); // Use navigate to change the route
    };

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

    return (
        <div style={{ margin: '20px', width: '90%' }}>
            <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>Asset Overview</h1></div>
            <input placeholder='Search for asset' onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px", borderRadius:'5px'}}></input>
            { getAdminStatus() && <button className='button' style={{marginLeft:'16px'}} onClick={create} >Create new Asset</button>}
            <DataTable
                columns={columns}
                data={tableData}
                progressPending={loading}
                pagination
                persistTableHead
                onRowClicked={handleRowClicked}
                customStyles={customStyles}
            />
        </div>
    );
}

export default Table;