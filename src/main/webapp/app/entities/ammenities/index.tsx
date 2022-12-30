import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ammenities from './ammenities';
import AmmenitiesDetail from './ammenities-detail';
import AmmenitiesUpdate from './ammenities-update';
import AmmenitiesDeleteDialog from './ammenities-delete-dialog';

const AmmenitiesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ammenities />} />
    <Route path="new" element={<AmmenitiesUpdate />} />
    <Route path=":id">
      <Route index element={<AmmenitiesDetail />} />
      <Route path="edit" element={<AmmenitiesUpdate />} />
      <Route path="delete" element={<AmmenitiesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AmmenitiesRoutes;
