import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import URL from "../../tools/URL";
import HTTPRequest from "../../tools/HTTPRequest";
import {useParams} from "react-router";
import {getAdminStatus} from "../../tools/globals";

function ServiceCompletedTable({ displayAllServices }) {
    const [cookies, setCookie, removeCookie] = useCookies();

    const [data, setData] = useState([]);
    const [searchData, setSearchData] = useState([]);
    const [tableData, setTableData] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        fetchData();
    }, []);

    const search = (event) => {
        setSearchData([]);
        data.forEach(element => {
            if(element.service.intervalName.toLowerCase().includes(event.target.value.toLowerCase()) || element.assetOnSite.assetOnSiteTag.toString().includes(event.target.value.toLowerCase()) || element.service.description.toLowerCase().includes(event.target.value.toLowerCase())) {
                searchData.push(element);
            }
            setTableData(searchData);
        });
    };

    const fetchData = async () => {
        setLoading(true);
        let endpoint = getAdminStatus() ? `${URL.BACKEND}/api/admin/servicesCompleted` : `${URL.BACKEND}/api/technician/servicesCompleted`;
        HTTPRequest.get(endpoint, cookies.JWT).then(response => {
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
            name: 'Service Description',
            selector: row => row.service?.description,
            sortable: true,
        },
        {
            name: 'Service Interval',
            selector: row => row.service?.intervalName,
            sortable: true,
        },
        {
            name: 'Site Name',
            selector: row => row.assetOnSite?.site.name,
            sortable: true,
        },
        {
            name: 'Asset Name',
            selector: row => row.assetOnSite?.asset.name,
            sortable: true,
        },
        {
            name: 'Asset On Site Tag',
            selector: row => row.assetOnSite?.assetOnSiteTag,
            sortable: true,
        },
        {
            name: 'Service Completion Time',
            selector: row => row.timeCompleted !== null ? formatLocalDateTime(row.timeCompleted) : 'Service Not Completed',
            sortable: true,
        },
    ];

    // Handler for row click event using navigate
    const handleRowClicked = (row) => {
        navigate(`${row.id}`); // Use navigate to change the route
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
            <div style={{ textAlign:"center" }}><h1 style={{fontSize:30, color:"#003341"}}>Requested Services Overview</h1></div>
            <input placeholder='Search for ongoing services' onChange={search} style={{marginBottom:"10px", minWidth:"25%", minHeight:"25px", borderRadius:'5px'}}></input>
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

export default ServiceCompletedTable;