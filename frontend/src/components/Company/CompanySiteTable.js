import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate, useParams } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import HTTPRequest from '../../tools/HTTPRequest';
import {jwtDecode} from "jwt-decode";
import URL from '../../tools/URL';

function Table() {
    const [cookies, setCookie, removeCookie] = useCookies();

    const [isAdmin, setIsAdmin] = useState();

    const { companyID } = useParams();

    const [data, setData] = useState([]);
    const [searchData, setSearchData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [title, setTitle] = useState();
    const navigate = useNavigate();

    const searchInput = useRef(null);
    const datatable = useRef(null);

    const search = () => {
        setSearchData([]);
        data.forEach(element => {
            if(element.name.toLowerCase().includes(searchInput.current.value.toLowerCase())) {
                searchData.push(element);
            } else if(element.id.toString().includes(searchInput.current.value.toLowerCase())) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const create = () => {
        navigate('create');
    }

    useEffect(() => {
        if(cookies.JWT != null) {
            setIsAdmin(false);
            jwtDecode(cookies.JWT).roles.forEach(role => {
                if(role.authority === "ADMIN") {
                    setIsAdmin(true);
                }
            });
        }
    }, []);

    useEffect(() => {
        if(isAdmin != null) {
            fetchData();
        }
    }, [isAdmin]);

    const fetchData = () => {
        HTTPRequest.get(`${URL.BACKEND}/api/admin/companies/${companyID}/sites`, cookies.JWT)
        .then(response => {
            if(response.data.length > 0) {
                setTitle(response.data[0].company.name + "'s site overview");
            }
            setData(response.data);
            setTableData(response.data);
            setLoading(false);
            console.log(response);
        })
        .catch(error => {setLoading(false)});
    };

    const columns = [
        {
            name: 'Name',
            selector: row => row.name,
            sortable: true,
        },
        {
            name: 'Company',
            selector: row => row.company.name,
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
        navigate(`/company/${row.company.id}/site/${row.id}`); // Use navigate to change the route
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
            <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>{title}</h1></div>
            <input placeholder='Search for Name or ID' ref={searchInput} onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px", borderRadius:'5px'}}></input>
            <button className='button' style={{marginLeft:'16px'}} onClick={create} >Create new site</button>
            <DataTable
                columns={columns}
                data={tableData}
                progressPending={loading}
                pagination
                persistTableHead
                onRowClicked={handleRowClicked}
                customStyles={customStyles}
                ref={datatable}
            />
        </div>
    );
}

export default Table;