export function renderComponent(component) {
    const content = document.getElementById('content');
    content.innerHTML = '';
    const element = document.createElement(component);
    content.appendChild(element);
}

export async function logout() {
    try {
        const response = await fetch('/logout', { method: 'GET' });

        if (response.ok) {
            window.location.href = '/index';
        } else {
            console.error('Error al hacer logout:', response.statusText);
        }
    } catch (error) {
        console.error('Error en la solicitud de logout:', error);
    }
}

window.renderComponent = renderComponent;
window.logout = logout;
