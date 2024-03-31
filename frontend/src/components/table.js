import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { format } from 'date-fns';
import { Link } from 'react-router-dom';
import './table.css'

function Table() {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/assets');
            setData(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const goToNextPage = () => setCurrentPage(current => Math.min(current + 1, totalPages - 1));
    const goToPreviousPage = () => setCurrentPage(current => Math.max(current - 1, 0));

    // Function to format the LocalDateTime to "dd.mm.yyyy hh:mm"
    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    return (
        <div className="table-container">
            <table className="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Creation Date</th>
                    <th>Active</th>
                </tr>
                </thead>
                <tbody>
                {data.map(asset => (
                    <tr key={asset.id}>
                        <td>{asset.name}</td>
                        <td>{asset.description}</td>
                        <td>{formatLocalDateTime(asset.creationDate)}</td>
                        <td>{asset.active ? 'Yes' : 'No'}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default Table;