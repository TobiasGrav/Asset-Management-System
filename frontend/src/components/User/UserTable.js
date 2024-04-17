import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import HTTPRequest from '../../tools/HTTPRequest';

function Table() {
    const [cookies, setCookie, removeCookie] = useCookies();

    const [data, setData] = useState([]);
    const [searchData, setSearchData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const searchInput = useRef(null);

    useEffect(() => {
        fetchData();
    }, []);

    const search = () => {
        setSearchData([]);
        data.forEach(element => {
            let fullName = element.firstName + " " + element.lastName
            if(fullName.toLowerCase().includes(searchInput.current.value)) {
                searchData.push(element);
            } else if(element.email.toLowerCase().includes(searchInput.current.value)) {
                searchData.push(element);
            } else if(element.phoneNumber.toLowerCase().includes(searchInput.current.value)) {
                searchData.push(element);
            } else if(element.id.toString().includes(searchInput.current.value)) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const create = () => {
        navigate('/asset/create');
    };

    const fetchData = () => {
        setLoading(true);
        HTTPRequest.get('http://localhost:8080/api/admin/users', cookies.JWT)
        .then(response => {
            console.log(response);
            setData(response.data);
            setTableData(response.data);
            setLoading(false);
        })
        .catch(error => {setLoading(false)});
    };

    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    const columns = [
        {
            name: 'Name',
            selector: row => row.firstName + " " + row.lastName,
            sortable: true,
        },
        {
            name: 'Email',
            selector: row => row.email,
            sortable: true,
        },
        {
            name: 'Phone number',
            selector: row => row.phoneNumber,
            sortable: true,
        },
        {
            name: 'Creation Date',
            selector: row => formatLocalDateTime(row.creationDate),
            sortable: true,
        },
        {
            name: 'Active',
            selector: row => row.active ? 'Yes' : 'No',
            sortable: true,
        },
    ];

    // Handler for row click event using navigate
    const handleRowClicked = (row) => {
        navigate(`/user/${row.id}`); // Use navigate to change the route
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
            <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>User Overview</h1></div>
            <input placeholder='Search for asset' ref={searchInput} onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px", borderRadius:'5px'}}></input>
            <button className='button' style={{marginLeft:'16px'}} onClick={create} >Create new Asset</button>
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