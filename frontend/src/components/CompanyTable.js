import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';

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
            if(element.name.toLowerCase().includes(searchInput.current.value)) {
                searchData.push(element);
            } else if(element.id.toString().includes(searchInput.current.value)) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const fetchData = async () => {
        setLoading(true);
        try {
            const response = await axios.get('http://localhost:8080/api/assets', {
                headers: {
                  Authorization: 'Bearer ' + cookies.JWT,
                  Accept: "application/json",
                  'Content-Type': "application/json"
                }});
            setData(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setLoading(false);
        }
    };

    const columns = [
        {
            name: 'Name',
            selector: row => row.name,
            sortable: true,
        },
        {
            name: 'ID',
            selector: row => row.id,
            sortable: true,
        },
    ];

    // Handler for row click event using navigate
    const handleRowClicked = (row) => {
        navigate(`/company/${row.id}`); // Use navigate to change the route
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
            <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>Company Overview</h1></div>
            <input placeholder='Search for company' ref={searchInput} onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px"}}></input>
            <DataTable
                columns={columns}
                data={data}
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