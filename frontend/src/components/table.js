import React, { useState, useEffect } from 'react';
import axios from 'axios';
import DataTable from 'react-data-table-component';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';

function Table() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        setLoading(true);
        try {
            const response = await axios.get('http://localhost:8080/api/assets');
            setData(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setLoading(false);
        }
    };

    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    const columns = [
        {
            name: 'Name',
            selector: row => row.name,
            sortable: true,
        },
        {
            name: 'Description',
            selector: row => row.description,
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
        navigate(`/asset/${row.id}`); // Use navigate to change the route
    };

    const customStyles = {
        headCells: {
            style: {
                backgroundColor: '#f2f2f2',
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
                    backgroundColor: '#f2f2f2', // Even rows background color
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
                backgroundColor: '#f2f2f2', // Pagination buttons background color
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
            <DataTable
                title="Assets"
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