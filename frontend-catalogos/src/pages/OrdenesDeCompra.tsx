import { Edit2, Save, Search, Trash2 } from "lucide-react";
import { useEffect, useMemo, useState } from "react";
import { useStore } from "../store/useStore";
import { SavedFilter } from "../types";

export function OrdenesDeCompra() {
  const purchaseOrders = useStore((state) => state.purchaseOrders);
  const setPurchaseOrders = useStore((state) => state.setPurchaseOrders);
  const savedFilters = useStore((state) => state.savedFilters);
  const setSavedFilters = useStore((state) => state.setSavedFilters);

  const [filters, setFilters] = useState({
    dateFrom: "",
    dateTo: "",
    status: "",
    storeCode: "",
  });

  const [filterName, setFilterName] = useState("");
  const [showSaveFilter, setShowSaveFilter] = useState(false);

  useEffect(() => {
    const fetchPurchaseOrders = async () => {
      try {
        const response = await fetch(
          "http://localhost:8081/api/ordencompra/todas",
          {
            headers: {
              Accept: "application/hal+json",
            },
          }
        );
        if (response.ok) {
          const data = await response.json();
          setPurchaseOrders(data);
        } else {
          console.error(
            "Failed to fetch purchase orders:",
            response.statusText
          );
        }
      } catch (error) {
        console.error("Error fetching purchase orders:", error);
      }
    };

    fetchPurchaseOrders();
  }, [setPurchaseOrders]);

  useEffect(() => {
    const savedFiltersSession = sessionStorage.getItem("savedFilters");
    if (savedFiltersSession) {
      setSavedFilters(JSON.parse(savedFiltersSession));
    }
  
    const filtersSession = sessionStorage.getItem("filters");
    if (filtersSession) {
      setFilters(JSON.parse(filtersSession));
    }
  }, [setSavedFilters, setFilters]);
  useEffect(() => {
    sessionStorage.setItem("filters", JSON.stringify(filters));
  }, [filters]);

  useEffect(() => {
    sessionStorage.setItem("savedFilters", JSON.stringify(savedFilters));
  }, [savedFilters]);

  const filteredOrders = useMemo(() => {
    return purchaseOrders.filter((order) => {
      const matchesDateFrom =
        !filters.dateFrom ||
        new Date(order.fechaSolicitud) >= new Date(filters.dateFrom);

      const matchesDateTo =
        !filters.dateTo ||
        new Date(order.fechaSolicitud) <= new Date(filters.dateTo);

      const matchesStatus = !filters.status || order.estado === filters.status;

      const matchesStore =
        !filters.storeCode ||
        order.tiendaId.toLowerCase().includes(filters.storeCode.toLowerCase());

      return matchesDateFrom && matchesDateTo && matchesStatus && matchesStore;
    });
  }, [purchaseOrders, filters]);

  const handleSaveFilter = () => {
    const newFilter: SavedFilter = {
      id: Date.now().toString(),
      name: filterName,
      ...filters,
    };
    setSavedFilters([...savedFilters, newFilter]);
    setShowSaveFilter(false);
    setFilterName("");
  };

  const handleDeleteFilter = (id: string) => {
    setSavedFilters(savedFilters.filter((f) => f.id !== id));
  };

  const handleApplyFilter = (filter: SavedFilter) => {
    setFilters({
      dateFrom: filter.dateFrom || "",
      dateTo: filter.dateTo || "",
      status: filter.status || "",
      storeCode: filter.storeCode || "",
    });
  };
  return (
    <div className="space-y-6">
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-lg font-medium mb-4">
          Reporte de Órdenes de Compra
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mb-4">
          <input
            type="date"
            placeholder="Desde"
            value={filters.dateFrom}
            onChange={(e) =>
              setFilters({ ...filters, dateFrom: e.target.value })
            }
            className="border rounded p-2"
          />
          <input
            type="date"
            placeholder="Hasta"
            value={filters.dateTo}
            onChange={(e) => setFilters({ ...filters, dateTo: e.target.value })}
            className="border rounded p-2"
          />
          <select
            value={filters.status}
            onChange={(e) => setFilters({ ...filters, status: e.target.value })}
            className="border rounded p-2"
          >
            <option value="">Todos los Estados</option>
            <option value="PENDIENTE">Pendiente</option>
            <option value="ACEPTADA">Aceptada</option>
            <option value="RECHAZADA">Rechazada</option>
          </select>
          <input
            type="text"
            placeholder="Código de Tienda"
            value={filters.storeCode}
            onChange={(e) =>
              setFilters({ ...filters, storeCode: e.target.value })
            }
            className="border rounded p-2"
          />
        </div>

        <div className="flex justify-between items-center mb-6">
          <button
            className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700 flex items-center"
            onClick={() =>
              setFilters({
                dateFrom: "",
                dateTo: "",
                status: "",
                storeCode: "",
              })
            }
          >
            <Search className="h-4 w-4 mr-2" />
            Limpiar Filtros
          </button>
          <button
            className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 flex items-center"
            onClick={() => setShowSaveFilter(true)}
          >
            <Save className="h-4 w-4 mr-2" />
            Guardar Filtro
          </button>
        </div>

        {showSaveFilter && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded-lg">
              <h3 className="text-lg font-medium mb-4">Guardar Filtro</h3>
              <input
                type="text"
                placeholder="Nombre del Filtro"
                value={filterName}
                onChange={(e) => setFilterName(e.target.value)}
                className="border rounded p-2 w-full mb-4"
              />
              <div className="flex justify-end space-x-2">
                <button
                  className="bg-gray-200 px-4 py-2 rounded"
                  onClick={() => setShowSaveFilter(false)}
                >
                  Cancelar
                </button>
                <button
                  className="bg-green-600 text-white px-4 py-2 rounded"
                  onClick={handleSaveFilter}
                >
                  Guardar
                </button>
              </div>
            </div>
          </div>
        )}

        {savedFilters.length > 0 && (
          <div className="mb-6">
            <h3 className="text-sm font-medium text-gray-700 mb-2">
              Filtros Guardados
            </h3>
            <div className="space-y-2">
              {savedFilters.map((filter) => (
                <div
                  key={filter.id}
                  className="flex items-center justify-between bg-gray-50 p-2 rounded"
                >
                  <span>{filter.name}</span>
                  <div className="space-x-2">
                    <button
                      className="text-indigo-600 hover:text-indigo-800"
                      onClick={() => handleApplyFilter(filter)}
                    >
                      <Edit2 className="h-4 w-4" />
                    </button>
                    <button
                      className="text-red-600 hover:text-red-800"
                      onClick={() => handleDeleteFilter(filter.id)}
                    >
                      <Trash2 className="h-4 w-4" />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  ID
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Estado
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Tienda
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Fecha de Solicitud
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Fecha de Envío
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Fecha de Recepción
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Observaciones
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {filteredOrders.map((order) => (
                <tr key={order.id}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {order.id}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span
                      className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                      ${
                        order.estado === "ACEPTADA"
                          ? "bg-green-100 text-green-800"
                          : order.estado === "RECHAZADA"
                          ? "bg-red-100 text-red-800"
                          : "bg-yellow-100 text-yellow-800"
                      }`}
                    >
                      {order.estado}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {order.tiendaId}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {new Date(order.fechaSolicitud).toLocaleDateString()}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {order.fechaEnvio
                      ? new Date(order.fechaEnvio).toLocaleDateString()
                      : "-"}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {order.fechaRecepcion
                      ? new Date(order.fechaRecepcion).toLocaleDateString()
                      : "-"}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {order.observaciones}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
