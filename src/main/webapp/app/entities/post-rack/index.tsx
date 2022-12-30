import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PostRack from './post-rack';
import PostRackDetail from './post-rack-detail';
import PostRackUpdate from './post-rack-update';
import PostRackDeleteDialog from './post-rack-delete-dialog';

const PostRackRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PostRack />} />
    <Route path="new" element={<PostRackUpdate />} />
    <Route path=":id">
      <Route index element={<PostRackDetail />} />
      <Route path="edit" element={<PostRackUpdate />} />
      <Route path="delete" element={<PostRackDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PostRackRoutes;
