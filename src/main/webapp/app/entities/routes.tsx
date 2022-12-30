import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PostRack from './post-rack';
import Review from './review';
import Ammenities from './ammenities';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="post-rack/*" element={<PostRack />} />
        <Route path="review/*" element={<Review />} />
        <Route path="ammenities/*" element={<Ammenities />} />
        <Route path="rack/*" element={<Rack />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
