document.getElementById('uploadForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];
    
    if (!file) {
        alert("Por favor, selecciona un archivo CSV.");
        return;
    }

    // Crear un FormData y agregar el archivo
    const formData = new FormData();
    formData.append('file', file);

    // Enviar el archivo al servidor usando fetch
    try {
        const response = await fetch('/api/OrdenDeCompra', {
            method: 'POST',
            body: formData
        });

        const result = await response.json();
        
        if (response.ok) {
            document.getElementById('result').innerHTML = `
                <h2>Órdenes creadas correctamente:</h2>
                <p>${result.message}</p>
                <ul>
                    ${result.tiendas.map(tienda => `<li>${tienda}</li>`).join('')}
                </ul>
            `;
        } else {
            document.getElementById('result').innerHTML = `
                <h2>Error:</h2>
                <p>${result.error}</p>
            `;
        }
    } catch (error) {
        console.error('Error al enviar el archivo:', error);
        document.getElementById('result').innerHTML = `
            <h2>Error de conexión</h2>
            <p>No se pudo conectar con el servidor.</p>
        `;
    }
});
